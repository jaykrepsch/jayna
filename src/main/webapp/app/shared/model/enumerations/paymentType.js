export const PaymentType = {
  TRANSFER: 'TRANSFER',
  SEPA_DEBIT: 'SEPA_DEBIT',
  CASH: 'CASH',
}

export function getPaymentTypes() {
  return Object.keys(PaymentType).filter(item => {
    return isNaN(Number(item));
  });
}
