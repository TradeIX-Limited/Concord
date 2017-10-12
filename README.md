![Concord](https://github.com/TXCTeam/Concord/blob/release-V1/Concord.png)

# Introduction

Welcome to **Concord**; a Corda solution written in partnership with R3 for project "Marco Polo". Concord is a proof of concept to bring Distributed Ledger Technology (DLT) into trade finance.



## Definitions

**Formal** agreement or harmony between people or groups.

**Musical** a chord that is pleasing or satisfactory in itself.



## Technical Documentation

### Participants

In the Concord application there are several participants:

- **Buyer** - Responsible for issuing a purchase order.
- **Supplier** - The counterparty for a purchase order, and the initial owner.
- **Conductor** - TradeIX / TIX application behind a Corda Oracle, which performs external validation.
- **Owner** - The owner of the purchase order for any given state. This could be a supplier or a funder.
- **Funder** - Responsible for funding a purchase order.

When unit testing, the following values will be used to represent each of the participants

- Buyer - `BOB` 
- Supplier - `Alice`
- Conductor - `BIG_CORP`
- Owner - `MEGA_CORP`