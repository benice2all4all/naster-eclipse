# naster-eclipse

A Spring Boot app with Discord OAuth login wired in.

## Requirements

- Java 21
- Maven 4+ via the included wrapper
- A Discord application with an OAuth2 redirect URL of `http://localhost:8080/login/oauth2/code/discord`

## Environment variables

Set these before starting the app with real Discord auth:

- `DISCORD_CLIENT_ID`
- `DISCORD_CLIENT_SECRET`

Example:

```bash
export DISCORD_CLIENT_ID=your-discord-client-id
export DISCORD_CLIENT_SECRET=your-discord-client-secret
```

## Run locally

```bash
./mvnw spring-boot:run
```

Then open http://localhost:8080/

## Auth flow

- `/` shows a Discord sign-in link
- `/me` requires login and returns the authenticated Discord user profile fields exposed by the provider
- Spring Security handles the OAuth callback at `/login/oauth2/code/discord`

## Test

```bash
./mvnw test
```
