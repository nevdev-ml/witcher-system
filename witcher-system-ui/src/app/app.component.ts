import {Component} from '@angular/core';
import {BeastsEnum} from './enums/beasts.enum';
import {RegionsEnum} from './enums/regions.enum';
import {CurrencyEnum} from './enums/currency.enum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // paths
  static API_URL = 'http://localhost:8080';
  static PATH_AUTH = AppComponent.API_URL + '/account/login';
  static PATH_LOGOUT = AppComponent.API_URL + '/account/logout';
  static PATH_REGISTER = AppComponent.API_URL + '/account/register';
  static PATH_PROFILE = AppComponent.API_URL + '/account/profile';

  static PATH_TASK = AppComponent.API_URL + '/task/details/';
  static PATH_TASKS = AppComponent.API_URL + '/task/tasks/';
  static PATH_QUESTS = AppComponent.API_URL + '/task/quests/';
  static PATH_ADD_TASK = AppComponent.API_URL + '/task/add/';
  static PATH_ACCEPT_TASK = AppComponent.API_URL + '/task/accept/';
  static PATH_CANCEL_TASK = AppComponent.API_URL + '/task/cancel/';
  // constants
  static TOKEN = 'token';
  static JWT_HEADER = 'Authorization';
  static ROLES = 'authorities';
  static ID = 'id';
  // error
  static NOT_AUTH = 'Not authorized';
  // messages
  static NAVIGATED = 'Navigated';
  static SUCCESS_REGISTER = 'Success register, navigated to login';
  // map
  static BeastMap = new Map<number, string>([
    [BeastsEnum.ALP, 'Альп'], [BeastsEnum.BASILISK, 'Василиск'], [BeastsEnum.BRUXA, 'Брукса'], [BeastsEnum.DRACONID, 'Драконид'],
    [BeastsEnum.DROWNER, 'Утопец'], [BeastsEnum.GHOUL, 'Гуль'], [BeastsEnum.GOLEM, 'Голем'], [BeastsEnum.KIKIMORE, 'Кикимора'],
    [BeastsEnum.NIGHTWRAITH, 'Ночница'], [BeastsEnum.STRIGA, 'Стрыга'], [BeastsEnum.WYVERN, 'Виверна'], [BeastsEnum.OTHER, 'Другое']
  ]);
  static RegionMap = new Map<number, string>([
    [RegionsEnum.AEDIRN, 'Аэдирн'], [RegionsEnum.BROKILON, 'Брокилон'], [RegionsEnum.CIDARIS, 'Цидарис'], [RegionsEnum.CINTRA, 'Цинтра'],
    [RegionsEnum.HENGFORS, 'Хенгфорс'], [RegionsEnum.KAEDWEN, 'Каэдвен'], [RegionsEnum.KOVIR, 'Ковир'], [RegionsEnum.LYRIA, 'Лирия'],
    [RegionsEnum.REDANIA, 'Редания'], [RegionsEnum.SKELLIGE, 'Скеллиге'], [RegionsEnum.TEMERIA, 'Темерия'], [RegionsEnum.VERDEN, 'Верден'],
    [RegionsEnum.AEDIRN, 'Аэдирн']
  ]);
  static CurrencyMap = new Map<number, string>([
    [CurrencyEnum.CROWN, 'Крона'], [CurrencyEnum.OREN, 'Орен'], [CurrencyEnum.DUCAT, 'Дукат']
  ]);
  title = 'witcher-system-ui';
}
