# Ultimatum-Game
MAS ultimatum game simulation in JAVA

The ultimatum game is a popular instrument of economics experiments to clarify notions of justice and reciprocity.
It consist of 2 players dealing with a sum of money. The first one is the proposer and is tasked with splitting the sum of money with the other one, the responder.
Once the proposer communicates his decision, the responder may accept or decline it.
If the responder accept, the money is split according to the proposal and if he declines both receive nothing.
Both know in advance the consequences of accepting or rejecting the proposal.

In this project we implement this game with 4 kind of agents with different behaviour that will be proposer and responder at the same time.
Another agent, the coordinator, is here to collect the result of each agent.
The number of agents evolving in the system is up to the user and can be set in the code.
The MAS platform JADE is used to implement the agent.

The coordinator agent:
Its role is to coordinate all agents and to gather the result at the end.

The king agent:
This agent will accept every proposal and will always offer to split the sum of money equally.

The honest agent:
This agent will only accept offers that are equally split and will reject others.
It will always split the offer equally when making a proposal.

The selfish agent:
This agent will only accept offers that	are equally split and will reject others as the honest agent.
When he makes a proposal he will always keep 90% of the sum for itself.

The greedy agent:
This agent will accept every offers and will always keep 90% of the sum for itself.

The aim of this project is determine which behaviour is the best to maximize the amount of money at the end, when a lot of agent are evolving in the same environment.
Each kind of agent are equally distributed in the system and when an agent makes a proposal he will choose another agent randomly

