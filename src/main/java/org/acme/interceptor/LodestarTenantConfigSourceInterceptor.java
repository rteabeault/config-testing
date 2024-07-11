package org.acme.interceptor;

import io.smallrye.config.RelocateConfigSourceInterceptor;
import jakarta.annotation.Priority;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jboss.logging.Logger;

public class LodestarTenantConfigSourceInterceptor extends RelocateConfigSourceInterceptor {
  private static final Logger log = Logger.getLogger(LodestarTenantConfigSourceInterceptor.class);

  private static final String[] SUPPORTED_EXTENSIONS = new String[]{
      "datasource",
      "liquibase"
  };

  private static final Pattern PATTERN =
      Pattern.compile(String.format("lodestar\\.tenant\\.(.*)\\.(%s)\\.(.*)",
          String.join("|", SUPPORTED_EXTENSIONS)));

  public LodestarTenantConfigSourceInterceptor() {
    super(name -> relocate(name));
  }

  private static String relocate(String name) {
    Matcher matcher = PATTERN.matcher(name);
    if (matcher.matches()) {
      String tenantName = matcher.group(1);
      String extensionName = matcher.group(2);
      String extensionConfigKey = matcher.group(3);
      String newKey = String.format("quarkus.%s.%s.%s", extensionName, tenantName, extensionConfigKey);
      log.infof("Relocating property key [%s] to [%s]", name, newKey);
      return newKey;
    } else {
      return name;
    }
  }

}