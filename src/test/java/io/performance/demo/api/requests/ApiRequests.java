package io.performance.demo.api.requests;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.ChainBuilder;
import io.performance.demo.api.logging.ApiTestLogger;

public class ApiRequests {

    // GET all users
    public static ChainBuilder getUsers() {
        return exec(
            http("GET /users")
                .get("/users")
                .check(status().is(200))
                .check(jsonPath("$.users").exists())
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            ApiTestLogger.logRequest("GET", "/users", session.get("statusCode").toString());
            ApiTestLogger.success("GET /users - Retrieved users successfully");
            return session;
        });
    }

    // GET single user by ID
    public static ChainBuilder getUserById() {
        return exec(
            http("GET /users/{id}")
                .get("/users/1")
                .check(status().is(200))
                .check(jsonPath("$.id").is("1"))
                .check(jsonPath("$.firstName").exists())
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            ApiTestLogger.logRequest("GET", "/users/1", session.get("statusCode").toString());
            ApiTestLogger.success("GET /users/1 - Retrieved user details");
            return session;
        });
    }

    // POST - Add new user with CSV data
    public static ChainBuilder addUser() {
        return exec(
            http("POST /users/add")
                .post("/users/add")
                .asJson()
                .body(StringBody(
                    "{" +
                    "\"firstName\":\"#{firstName}\"," +
                    "\"lastName\":\"#{lastName}\"," +
                    "\"age\":#{age}," +
                    "\"email\":\"#{email}\"" +
                    "}"
                ))
                .check(status().in(200, 201))
                .check(jsonPath("$.firstName").saveAs("createdUserName"))
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            String userName = session.getString("createdUserName");
            ApiTestLogger.logRequest("POST", "/users/add", session.get("statusCode").toString());
            ApiTestLogger.success("POST /users/add - Created user: " + userName);
            return session;
        });
    }

    // PUT - Update user
    public static ChainBuilder updateUser() {
        return exec(
            http("PUT /users/{id}")
                .put("/users/1")
                .asJson()
                .body(StringBody(
                    "{" +
                    "\"firstName\":\"UpdatedName\"," +
                    "\"lastName\":\"UpdatedLastName\"" +
                    "}"
                ))
                .check(status().is(200))
                .check(jsonPath("$.firstName").is("UpdatedName"))
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            ApiTestLogger.logRequest("PUT", "/users/1", session.get("statusCode").toString());
            ApiTestLogger.success("PUT /users/1 - User updated successfully");
            return session;
        });
    }

    // DELETE user
    public static ChainBuilder deleteUser() {
        return exec(
            http("DELETE /users/{id}")
                .delete("/users/1")
                .check(status().is(200))
                .check(jsonPath("$.isDeleted").is("true"))
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            ApiTestLogger.logRequest("DELETE", "/users/1", session.get("statusCode").toString());
            ApiTestLogger.success("DELETE /users/1 - User deleted successfully");
            return session;
        });
    }

    // GET products
    public static ChainBuilder getProducts() {
        return exec(
            http("GET /products")
                .get("/products")
                .check(status().is(200))
                .check(jsonPath("$.products").exists())
                .check(jsonPath("$.total").saveAs("totalProducts"))
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            Integer total = session.getInt("totalProducts");
            ApiTestLogger.logRequest("GET", "/products", session.get("statusCode").toString());
            ApiTestLogger.success("GET /products - Total products: " + total);
            return session;
        });
    }

    // Search products
    public static ChainBuilder searchProducts() {
        return exec(
            http("GET /products/search")
                .get("/products/search?q=phone")
                .check(status().is(200))
                .check(jsonPath("$.products").exists())
                .check(status().saveAs("statusCode"))
        )
        .exec(session -> {
            ApiTestLogger.logRequest("GET", "/products/search?q=phone", session.get("statusCode").toString());
            ApiTestLogger.success("GET /products/search - Search completed");
            return session;
        });
    }
}
