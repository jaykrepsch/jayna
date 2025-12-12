export const PaymentType = {
  ACTIVE: "ACTIVE",
  ARCHIVE: "ARCHIVE",
};

export function getStates() {
  return Object.keys(PaymentType).filter((item) => {
    return isNaN(Number(item));
  });
}
