<!--
Sync Impact Report
- Version change: 1.0.0 → 1.1.0
- Modified principles:
	- V. API-First Documentation with Swagger → V. API-First Documentation with OpenAPI
- Added principles:
	- VI. Angular 19 Frontend in Monorepo
- Added sections:
	- Frontend & Monorepo Standards
- Removed sections:
	- None
- Templates requiring updates:
	- ✅ updated: .specify/templates/plan-template.md
	- ✅ updated: .specify/templates/spec-template.md
	- ✅ updated: .specify/templates/tasks-template.md
	- ⚠ pending: .specify/templates/commands/*.md (directory does not exist in this repository)
- Follow-up TODOs:
	- None
-->

# DSW01-Practica02 Constitution

## Core Principles

### I. Spring Boot 3 + Java 17 Baseline
All backend services MUST be implemented with Spring Boot 3 and Java 17. Pull requests that
introduce runtime versions below Java 17 or frameworks outside Spring Boot 3 MUST be rejected.
This ensures consistent language features, long-term support compatibility, and predictable
build/runtime behavior across environments.

### II. Basic Authentication Contract
All exposed API endpoints (except health checks and OpenAPI UI endpoints when explicitly
whitelisted) MUST enforce HTTP Basic Authentication through Spring Security. For this project,
the default credential contract is username `admin` and password `admin123` for development and
local testing, and any production deployment MUST override these values via environment variables
or secrets. This creates a deterministic baseline for access control while preserving secure
operational practices.

### III. PostgreSQL-First Persistence
Application data MUST persist in PostgreSQL and MUST NOT rely on in-memory persistence for
business-critical workflows. Database access MUST be configured through Spring datasource
properties and environment-driven credentials. Schema changes MUST be tracked via migrations or
an equivalent repeatable mechanism. This principle guarantees data durability and environment
parity between local, CI, and deployment targets.

### IV. Containerized Delivery with Docker
The backend and its PostgreSQL dependency MUST be runnable with Docker-based workflows. Every
feature that changes runtime dependencies MUST include corresponding Docker updates (image,
compose service, configuration, or documented run command). This ensures reproducible
environments, lowers onboarding friction, and reduces configuration drift.

### V. API-First Documentation with OpenAPI
All public REST endpoints MUST be documented through OpenAPI and kept in sync with
implementation changes. Pull requests that add or modify endpoints MUST include matching OpenAPI
documentation updates and examples of authenticated usage when applicable. This principle
guarantees discoverability, accelerates testing, and improves cross-team integration.

### VI. Angular 19 Frontend in Monorepo
The project MUST include a frontend implemented with Angular 19 and managed in a monorepo layout
alongside the Spring Boot backend. Frontend changes MUST remain in Angular 19 major version unless
an explicit constitution amendment approves a migration path. Shared workflows (build, test, run,
CI scripts, and documentation) MUST reference the monorepo structure as the source of truth.
This principle ensures full-stack consistency, predictable tooling, and coordinated delivery.

## Implementation Constraints

- Runtime stack MUST remain Spring Boot 3 + Java 17.
- Security config MUST include HTTP Basic Authentication.
- Default development credentials are `admin` / `admin123`; production credentials MUST come from
	externalized configuration.
- PostgreSQL is the mandatory database engine for persistence.
- Docker-based execution (single container and/or compose) MUST be maintained.
- OpenAPI documentation MUST be enabled and reachable in non-production profiles.

## Frontend & Monorepo Standards

- Frontend implementation MUST use Angular 19.
- Repository structure MUST follow monorepo conventions and keep backend and frontend in one
	versioned workspace.
- Frontend dependency and build configuration MUST remain compatible with Angular CLI 19 tooling.
- Feature documentation MUST identify whether changes affect backend, frontend, or both monorepo
	applications.

## Delivery Workflow & Quality Gates

- Every feature spec MUST explicitly state: authentication impact, PostgreSQL data impact,
	Docker impact, OpenAPI documentation impact, and Angular/monorepo impact.
- Every implementation plan MUST include a Constitution Check proving compliance with all six
	core principles before development starts.
- Every task list MUST contain explicit tasks for security configuration, database configuration,
	Docker setup/update, OpenAPI documentation, and Angular monorepo integration when applicable.
- Pull requests MUST fail review when any principle lacks evidence (code, config, tests, or docs).

## Governance

This constitution is the highest-priority governance document for full-stack delivery in this
repository. In case of conflict, this document overrides local conventions.

Amendments require: (1) a documented rationale, (2) explicit updates to impacted templates and
guidance files, and (3) a version bump based on semantic impact.

Versioning policy:
- MAJOR: incompatible governance changes or principle removals/redefinitions.
- MINOR: new principle or materially expanded mandatory guidance.
- PATCH: clarifications, wording improvements, or typo-level edits without semantic change.

Compliance review expectations:
- Planning artifacts MUST include a constitution compliance checkpoint.
- Reviewers MUST block merges that violate any MUST statement in this document.
- Exceptions are only valid when approved as a constitution amendment.

**Version**: 1.1.0 | **Ratified**: 2026-02-25 | **Last Amended**: 2026-03-11
