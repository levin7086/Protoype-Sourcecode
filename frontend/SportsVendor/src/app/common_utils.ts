export function currencyFormatter(value: number, sign: any) {
  var sansDec = value.toFixed(2);
  var formatted = sansDec.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  return sign + `${formatted}`;
}
