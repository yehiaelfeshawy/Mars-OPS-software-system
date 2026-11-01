# MARS-OPS Software System

Mars colony operations management software. Coordinates human personnel and robotic units across facilities, manages mission assignment and execution, tracks resource consumption, responds to emergencies, and models environmental effects on colony activities.

## Design Rationale

| Relationship | Java Implementation | Classes |
|--------------|---------------------|---------|
| **Inheritance** | `extends` / `abstract` | `ColonyMembers` → `HumanPersonnel` / `RoboticUnit`; `Mission` → mission types; `Emergency` → emergency types; `Facility` → `Habitat` / `Laboratory` |
| **Association** | Field references, independent lifecycles | `MarsColony` ↔ `ColonyMembers`; `Mission` ↔ `ColonyMembers`; `Emergency` → `MarsColony` |
| **Aggregation** | "Has-a", parts exist independently | `Facility` ⊕ `FacilityResources`; static `FacilityResources.facilities` registry |
| **Composition** | Strong ownership, part lifecycle managed by whole | `Facility` ⟹ `FacilityResources` (instantiated via `setResources()`) |

Polymorphism replaces `instanceof` checks: `ColonyMembers.canPerform(Mission)` and `ColonyMembers.canHandle(EmergencyType)` are overridden in each concrete agent class.

## Resource Allocation Policy (§5.1)

- `MarsColony.getTotalResource(ResourceType)` sums quantities across operational facilities only.
- `MarsColony.hasResources(Map)` checks colony-wide totals per resource type.
- Resources are drawn from operational facilities sequentially until each requested quantity is satisfied.
- All-or-nothing: `consumeResources(Map)` returns `false` and consumes **0** resources if any colony total falls short; no partial deduction occurs.

## Emergency Rules (§9.1 & §9.2)

`MarsColony.findResponder(EmergencyType)` returns the first available member where `member.canHandle(type)` is true.

| Emergency | Required Responders |
|-----------|---------------------|
| `OXYGEN_LEAK` | 1 Engineer + 1 RepairRobot |
| `FIRE` | 1 Engineer or 1 MedicalOfficer |
| `ROVER_BREAKDOWN` | 1 Engineer or RepairRobot or ExplorationRover |
| `MEDICAL` | 1 MedicalOfficer |

**Oxygen Leak double-loss penalty (§9.2):**
- Initial `loss` deducted from habitat oxygen on detection.
- If unresolved (missing responders or insufficient resources), `loss` deducted a second time.
- Resource levels floored at 0.

## Environmental Restrictions (§8)

- `Environment.checkSurfaceOperations()` returns `false` during `Dust Storm`.
- `Environment.checkCommunication()` returns `false` during `Communication Disruption`.
- `ExplorationMission` and `RescueMission` require both surface operations and communication.
- Environment change while a mission is `IN_PROGRESS` fails the active mission.

## Mission Lifecycle (§7)

| State | Transition Condition |
|-------|---------------------|
| `ASSIGNED` | `submitMission()` passes validation; agents reserved, resources consumed |
| `IN_PROGRESS` | First `advanceTime()` tick when `isAllowedIn(environment)` is true |
| `COMPLETED` | `progress >= duration` |
| `FAILED` | `mission.fail(reason)` on rejection, environment interruption, or resource shortage |

On `COMPLETED` or `FAILED`, `releaseAgents()` restores all assigned agents to available and clears the active mission.

## Execution Commands

```bash
javac -d out *.java
java -cp out MarsOpsProject
```

## Test Harness (§12)

Five isolated scenarios run sequentially in `MarsOpsProject.main()`:

1. **Successful Exploration** — Scientist + Exploration Rover execute a 3-tick Exploration Mission; colony status and agent availability verified before and after.
2. **Mission Rejection** — Scientist assigned to Repair Mission; assertion of immediate failure with prescribed capability message.
3. **Resource Shortage** — Repair Mission requests 999,999 spare parts; assertion of rejection with 0 resources consumed.
4. **Emergency Handling** — Oxygen Leak triggered; Engineer and RepairRobot dispatched, resources consumed, emergency resolved.
5. **Environmental Effect** — Environment set to Dust Storm; Exploration Mission rejected with surface-operations message.
