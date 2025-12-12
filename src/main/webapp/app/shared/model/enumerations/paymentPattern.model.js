export const PaymentPattern = {
  ONETIME: "ONETIME",
  DAILY: "DAILY",
  WEEKLY: "WEEKLY",
  MONTHLY: "MONTHLY",
  TWOMONTHLY: "TWOMONTHLY",
  QUARTERLY: "QUARTERLY",
  SEMIANNUAL: "SEMIANNUAL",
  ANNUAL: "ANNUAL",
};

export function getPaymentPatterns() {
  return Object.keys(PaymentPattern).filter((item) => {
    return isNaN(Number(item));
  });
}
