const baseURL = "http://localhost:8080/jblog/api";

export const URLs = {
    blog: {
        base: baseURL + "/blog",
        create: baseURL + "/blog/create",
        login: baseURL + "/blog/login",
        auth: baseURL + "/blog/auth"
    },
    post: {
        base: baseURL + "/posts",
        create: baseURL + "/posts/create"
    },
    comment: {
        base: baseURL + "/comments",
        create: baseURL + "/comments/create"
    }
}