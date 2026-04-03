# Gamification Service — Project Report

**Date:** April 3, 2026
**Build Status:** PASSING
**Test Result:** 132 / 132 passed — 0 failures, 0 errors

---

## 1. Project Overview

The Gamification Service is a Spring Boot 3.5.13 microservice that manages gamification features for a learning platform. It handles XP tracking, badges, streaks, leaderboards, challenges, and reward rules. Persistence is backed by AWS DynamoDB (AWS Java SDK v1), and all endpoints are secured with JWT-based role authentication.

---

## 2. Technology Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.5.13 |
| Language | Java 17 |
| Database | AWS DynamoDB (aws-java-sdk-dynamodb 1.12.700) |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Boilerplate | Lombok |
| Testing | JUnit 5, Mockito 5, Spring Boot Test, Spring Security Test |
| Build | Maven (mvnw wrapper) |
| Server Port | 8087 |

---

## 3. Project Structure

```
Gamification_Service/
├── pom.xml
└── src/
    ├── main/java/com/gamification/streaks/
    │   ├── StreaksApplication.java
    │   ├── config/
    │   │   ├── DynamoDbConfig.java          # AmazonDynamoDB + DynamoDBMapper beans
    │   │   ├── JwtFilter.java               # OncePerRequestFilter — JWT extraction
    │   │   ├── JwtUtil.java                 # Token parsing, validation, claim extraction
    │   │   └── SecurityConfig.java          # Role-based HTTP security rules
    │   ├── controllers/                     # 9 REST controllers
    │   ├── dto/                             # 9 DTO classes
    │   ├── enums/                           # 8 enums
    │   ├── execption/                       # GlobalExceptionHandler + custom exceptions
    │   ├── model/                           # 9 DynamoDB entity classes
    │   ├── repository/                      # 9 DynamoDBMapper-based repositories
    │   └── service/
    │       ├── (9 service interfaces)
    │       └── Impl/ (9 service implementations)
    └── test/java/com/gamification/streaks/
        ├── config/JwtUtilTest.java
        ├── controller/ (8 controller test classes)
        ├── exception/GlobalExceptionHandlerTest.java
        └── service/ (9 service test classes)
```

---

## 4. Domain Entities

| Entity | DynamoDB Table | Hash Key | Key Fields |
|---|---|---|---|
| Badge | Badge | badgeId | badgeName, badgeType, xpReward, eligibilityRule, active |
| Challenge | Challenge | challengeId | challengeName, challengeType, rewardXP, startDate, endDate, active |
| GamificationRule | GamificationRule | ruleId | ruleName, ruleType, config, enabled, version, abCohort |
| LeaderBoardEntry | LeaderBoardEntry | leaderboardId | leaderboardType, userId, xpScore, rank, periodStart, periodEnd |
| RewardRule | RewardRule | ruleId | ruleName, ruleType, conditionType, conditionValue, rewardType, rewardValue |
| StreakRecord | StreakRecord | streakId | userId, streakType, currentStreakCount, longestStreakCount, streakStatus |
| UserBadge | UserBadge | userBadgeId | userId, badgeId, badgeName, xpAwarded |
| UserGamificationProfile | UserGamificationProfile | userId | role, totalXp, currentLevel, longestStreak, totalBadges, totalReward |
| XpTransaction | XpTransaction | xpTransactionId | userId, xpAmount, xpSource, referenceId, description |

---

## 5. Enums

| Enum | Values |
|---|---|
| BatchType | STREAK, PERFORMANCE, ENGAGEMENT, LEADERBOARD, SPECIAL |
| LeaderBoardType | GLOBAL, DAILY, WEEKLY, MONTHLY |
| RewardType | XP, BADGE, COINS, POINTS |
| RuleType | XP, BADGE, STREAK, LEADERBOARD, REWARD |
| StreakStatus | ACTIVE, BROKEN, PAUSED |
| StreakType | XP, BADGE, STREAK, LEADERBOARD, REWARD |
| UserRole | LEARNER, TRAINER |
| XpSource | LOGIN, QUIZ_COMPLETION, COURSE_COMPLETION, LAB_COMPLETION, LIVE_SESSION, STREAK_BONUS, CHALLENGE_COMPLETION |

---

## 6. REST API Endpoints

### Security Rules
- `GET /api/**` — LEARNER and TRAINER
- `POST /api/**` — TRAINER only
- `PUT /api/**` — TRAINER only
- `DELETE /api/**` — TRAINER only

### Badge `/api/badge`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | Badge entity |
| POST | /create | String |
| GET | /{badgeId} | BadgeDto |
| GET | /all | List\<BadgeDto\> |
| PUT | /{badgeId} | String |
| DELETE | /{badgeId} | String |

### Challenge `/api/challenge`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| GET | /all | List\<ChallengeDto\> |
| PUT | /{challengeId} | String |
| DELETE | /{challengeId} | String |

### GamificationRule `/api/gamification-rule`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| PUT | /{ruleId} | List\<GamificationRuleDto\> |
| DELETE | /{ruleId} | String |

### LeaderBoard `/api/leaderboard`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | LeaderBoardEntry entity |
| POST | /create | String |
| GET | /all | List\<LeaderBoardEntryDto\> |
| PUT | /{leaderboardId} | String |
| DELETE | /{leaderboardId} | String |

### RewardRule `/api/reward-rule`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| GET | /all | List\<RewardRuleDto\> |
| PUT | /{ruleId} | String |
| DELETE | /{ruleId} | String |

### StreakRecord `/api/streak-record`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| GET | /all | List\<StreakRecordDto\> |
| PUT | /{streakId} | String |
| DELETE | /{streakId} | String |

### UserBadge `/api/user-badge`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| GET | /all | List\<UserBadgeDto\> |
| PUT | /{userBadgeId} | String |
| DELETE | /{userBadgeId} | String |

### UserGamificationProfile `/api/user-gamification`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | UserGamificationProfile entity |
| POST | /create | String |
| GET | /{userId} | UserGamificationProfileDto |
| GET | /all | List\<UserGamificationProfileDto\> |
| PUT | /{userId} | String |
| DELETE | /{userId} | String |

### XpTransaction `/api/xp-transaction`
| Method | Path | Returns |
|---|---|---|
| POST | /create-dto | String |
| POST | /create | String |
| GET | /all | List\<XpTransactionDto\> |
| PUT | /{xpTransactionId} | String |
| DELETE | /{xpTransactionId} | String |

---

## 7. Security Architecture

```
HTTP Request
    ↓
JwtFilter (OncePerRequestFilter)
    ↓  Extract "Authorization: Bearer <token>"
JwtUtil.validateToken()
    ↓  valid → extract userId (subject) + role (claim)
UsernamePasswordAuthenticationToken set in SecurityContext
    ↓
SecurityConfig role-based rules applied
    ↓
Controller
```

JWT tokens are signed with HMAC-SHA256. The `role` claim maps to `ROLE_LEARNER` or `ROLE_TRAINER`.

---

## 8. Exception Handling

| Exception | HTTP Status |
|---|---|
| NotificationNotFoundException | 404 NOT FOUND |
| BadRequestException | 400 BAD REQUEST |
| Exception (catch-all) | 500 INTERNAL SERVER ERROR |

All responses return `ErrorResponse { message, status, timestamp }`.

---

## 9. Known Issues

| # | Location | Issue |
|---|---|---|
| 1 | `UserGamificationProfile` | `@DynamoDBAttribute(attributeName = "adminId")` on the `role` field — attribute name mismatch |
| 2 | `UserGamificationProfileServiceImpl.deleteUserGamificationProfile` | Calls `repository.delete(String.valueOf(profile))` — passes object toString instead of userId |
| 3 | `BadgeRepository.delete` | Null check is `if (badgeId != null)` instead of `if (badge != null)` — will NPE when badge is not found |
| 4 | `StreakRecordDto` | Field named `StreakType` (uppercase S) — violates Java naming convention |
| 5 | `GamificationRuleService.update` | Returns `List<GamificationRuleDto>` — inconsistent with all other update methods that return `String` |
| 6 | `application.yaml` | Placeholder credentials `YOUR_ACCESS_KEY` / `YOUR_SECRET_KEY` — must be replaced before deployment |
| 7 | `application.yaml` | `jwt.secret` property is missing — application will fail to start without it |

---

## 10. Test Report

### Final Run — April 3, 2026

```
Tests run: 132, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Total time: 58.411 s
```

### Breakdown by Category

| Category | Classes | Tests | Result |
|---|---|---|---|
| Service Unit Tests | 9 | 84 | ALL PASS |
| Controller Tests | 8 | 43 | ALL PASS |
| Config Tests (JwtUtil) | 1 | 6 | ALL PASS |
| Exception Handler Tests | 1 | 5 | ALL PASS |
| **TOTAL** | **19** | **132** | **ALL PASS** |

### Service Tests Detail

| Test Class | Tests | Key Scenarios |
|---|---|---|
| BadgeServiceImplTest | 10 | create (DTO+entity), getById (found/not found), getAll (list/empty), update (success/not found), delete (success/not found) |
| ChallengeServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |
| GamificationRuleServiceImplTest | 6 | create (DTO+entity), update (success/not found), delete (success/not found) |
| LeaderBoardServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |
| RewardRuleServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |
| StreakRecordServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |
| UserBadgeServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |
| UserGamificationProfileServiceImplTest | 9 | create (DTO+entity), getById (found/not found), getAll (list/empty), update (success/not found), delete (not found) |
| XpTransactionServiceImplTest | 8 | create (DTO+entity), getAll (list/empty), update (success/not found), delete (success/not found) |

### Controller Tests Detail

| Test Class | Tests | Key Scenarios |
|---|---|---|
| BadgeControllerTest | 7 | All 6 endpoints + LEARNER blocked on POST (403) |
| ChallengeControllerTest | 6 | All 5 endpoints + LEARNER blocked on DELETE (403) |
| GamificationRuleControllerTest | 4 | All 4 endpoints |
| LeaderBoardControllerTest | 5 | All 5 endpoints |
| RewardRuleControllerTest | 5 | All 5 endpoints |
| StreakRecordControllerTest | 5 | All 5 endpoints |
| UserBadgeControllerTest | 5 | All 5 endpoints |
| UserGamificationProfileControllerTest | 6 | All 6 endpoints |
| XpTransactionControllerTest | 5 | All 5 endpoints |

### Config / Exception Tests Detail

| Test Class | Tests | Key Scenarios |
|---|---|---|
| JwtUtilTest | 6 | validateToken (valid/expired/invalid), extractUserId, extractRole (TRAINER/LEARNER) |
| GlobalExceptionHandlerTest | 5 | 404 for NotFound, 400 for BadRequest, 500 for generic, message propagation |

---

## 11. Build & Run

```bash
# Run all tests
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home ./mvnw test

# Build JAR
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home ./mvnw package -DskipTests

# Run application (requires local DynamoDB on port 8000)
java -jar target/*.jar \
  --aws.accessKey=YOUR_KEY \
  --aws.secretKey=YOUR_SECRET \
  --jwt.secret=your-minimum-32-char-secret
```
