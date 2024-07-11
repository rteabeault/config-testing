package org.acme;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.quarkus.agroal.runtime.DataSourcesJdbcRuntimeConfig;
import io.quarkus.datasource.runtime.DataSourcesBuildTimeConfig;
import io.quarkus.datasource.runtime.DataSourcesRuntimeConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import java.util.Map;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(LodestarTenantConfigSourceInterceptorTest.class)
public class LodestarTenantConfigSourceInterceptorTest implements QuarkusTestProfile {

  @Inject
  DataSourcesBuildTimeConfig dsBuildTimeConfig;

  @Inject
  DataSourcesRuntimeConfig dsRuntimeConfig;

  @Inject
  DataSourcesJdbcRuntimeConfig dsJdbcRuntimeConfig;

  @Test
  void testTenantConfig() {
    assertThat(dsBuildTimeConfig.dataSources().get("t2").dbKind().get(), is("postgresql"));
    assertThat(dsRuntimeConfig.dataSources().get("t2").password().get(), is("connor"));
    assertThat(dsRuntimeConfig.dataSources().get("t2").username().get(), is("sarah"));
    assertThat(dsJdbcRuntimeConfig.dataSources().get("t2").jdbc().url().get(),
        is("jdbc:postgresql://localhost:5432/mydatabase"));
  }

  //Using this works.
//  @Override
//  public String getConfigProfile() {
//    return "interceptor";
//  }

    // Using this does not work.
  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of(
        "quarkus.datasource.devservices.enabled","false",
        "quarkus.datasource.t2.devservices.enabled", "false",
        "quarkus.config.mapping.validate-unknown", "false",

        // These should get relocated to quarkus.datasource.t2...
        "lodestar.tenant.t2.datasource.username", "sarah",
        "lodestar.tenant.t2.datasource.password", "connor",
        "lodestar.tenant.t2.datasource.jdbc.url", "jdbc:postgresql://localhost:5432/mydatabase",
        "lodestar.tenant.t2.datasource.db-kind", "postgresql");
  }
}