# config-testing

Demonstrates a bug in Quarkus where `QuarkusTestProfile.getConfigOverrides()` in conjunction with
a `RelocateConfigSourceInterceptor` does not work as expected.

https://github.com/quarkusio/quarkus/issues/41849
