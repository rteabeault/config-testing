# config-testing

Demonstrates a bug in Quarkus where `QuarkusTestProfile.getConfigOverrides()` in conjunction with
a `RelocateConfigSourceInterceptor` does not work as expected.

https://github.com/quarkusio/quarkus/issues/41849

https://quarkusio.zulipchat.com/#narrow/stream/187030-users/topic/Property.20name.20relocation.20.20not.20working.20as.20expected.2E/near/450587447
