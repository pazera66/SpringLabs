Lab 04 - testing basics
==
Topics
--
* How to write unit tests using JUnit.

* How to write integration (module) tests using [Spring Test Framework][1].

Instructions
--
1. Enable `Merchant` to store a `PaybackPolicy`.

2. Unit test `Merchant`s logic using provided stub in `MerchantTest`.

3. Run all tests to find integration points that need correcting.

4. Fix `JdbcMerchantRepository` by providing appropriate policy identified by name among `PaybackPolices`.

5. [Improve][2] `PaybackBookKeeperModuleTest` using supporting classes from [Spring Test Framework][1].

[Back to index](..)

 [1]: http://docs.spring.io/spring/docs/3.2.6.RELEASE/spring-framework-reference/html/testing.html
 [2]: http://docs.spring.io/spring/docs/3.2.6.RELEASE/spring-framework-reference/html/testing.html#integration-testing-goals
