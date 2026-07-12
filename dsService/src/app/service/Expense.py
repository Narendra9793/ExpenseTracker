from typing import Optional
from pydantic import BaseModel, Field

class Expense(BaseModel):
    amount: Optional[str] = Field(
        title="expense",
        description="Expense made in the transaction"
    )

    currency: Optional[str] = Field(
        title="currency",
        description="Currency of the transaction"
    )

    merchant: Optional[str] = Field(
        title="merchant",
        description="Merchant name whom with transaction was made"
    )

    user_id: Optional[str] = Field(
        title="user_id",
        description="User ID of the user who made the transaction"
    )

    def serialize(self):
        return {
            "amount" : self.amount,
            "merchant": self.merchant,
            "currency":self.currency,
            "user_id":self.user_id
        }
