export class TaskModifyUtil {
  beasts: {name: string}[];
  currency: {name: string}[];
  locations: {name: string}[];

  constructor(beasts: {name: string}[], currency: {name: string}[], locations: {name: string}[]){
    this.beasts = beasts;
    this.currency = currency;
    this.locations = locations;
  }
}
