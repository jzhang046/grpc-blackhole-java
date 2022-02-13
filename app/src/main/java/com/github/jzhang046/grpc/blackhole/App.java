package com.github.jzhang046.grpc.blackhole;

public class App {
    public String getGreeting() {

        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
