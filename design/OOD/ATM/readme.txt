Problem: design ATM machine.

Clarify:
1 input a card (credit card or debit card)


Core Objects:
1 Card (Debit card, Credit card)
2 Account (checking account, saving account )
3 ATM manager


Cases:
0 user insert a card
1 user input password
2 user select a account
3 query account balance
4 deposit money to a account
5 withdraw money from a account
6 log out


Class Diagram:

Card

Debit Card implements Card

Credit Card implements Card


Account

Checking Account implements Account

Saving Account implements Accont


Session


ATMManger{

}

Correctness:
