import {BeastsEnum} from '../enums/beasts-enum';
import {RegionsEnum} from '../enums/regions-enum';
import {CurrencyEnum} from '../enums/currency-enum';
import { RolesEnum } from '../enums/roles-enum';

export class Constants {
  static API_URL = 'http://localhost:8080';
  static PATH_AUTH = Constants.API_URL + '/account/login';
  static PATH_LOGOUT = Constants.API_URL + '/account/logout';
  static PATH_REGISTER = Constants.API_URL + '/account/register';
  static PATH_PROFILE = Constants.API_URL + '/account/profile';
  static PATH_FORGOT_PASSWORD = Constants.API_URL + '/account/forgot/';
  static PATH_RESET_PASSWORD = Constants.API_URL + '/account/reset/';
  static PATH_EDIT_USER = Constants.API_URL + '/account/edit';
  static PATH_EDIT_USER_PASSWORD = Constants.API_URL + '/account/edit-password';

  static PATH_GET_USERS = Constants.API_URL + '/account/users';
  static PATH_DELETE_USER = Constants.API_URL + '/account/delete';
  static PATH_ENABLE_USER = Constants.API_URL + '/account/enable';
  static PATH_DISABLE_USER = Constants.API_URL + '/account/disable';

  static PATH_TASK = Constants.API_URL + '/task/details/';
  static PATH_TASKS = Constants.API_URL + '/task/tasks/';
  static PATH_QUESTS = Constants.API_URL + '/task/quests/';
  static PATH_CUSTOMER_QUESTS = Constants.API_URL + '/task/customer-quests/';
  static PATH_ADD_TASK = Constants.API_URL + '/task/add/';
  static PATH_EDIT_TASK = Constants.API_URL + '/task/edit/';
  static PATH_DELETE_TASK = Constants.API_URL + '/task/delete/';

  static PATH_ACCEPT_TASK = Constants.API_URL + '/task/accept/';
  static PATH_CANCEL_TASK = Constants.API_URL + '/task/cancel/';
  static PATH_COMPLETE_TASK = Constants.API_URL + '/task/complete/';
  static PATH_REWARD_TASK = Constants.API_URL + '/task/reward/';
  static PATH_REFUSE_TASK = Constants.API_URL + '/task/refuse/';

  static PATH_DEALS = Constants.API_URL + '/deal/deals/';
  static PATH_WORKSHOP = Constants.API_URL + '/deal/workshop/';
  static PATH_SHOP = Constants.API_URL + '/deal/shop/';
  static PATH_DEAL = Constants.API_URL + '/deal/details/';
  static PATH_ACCEPTED_DEALS = Constants.API_URL + '/deal/accepted-deals/';
  static PATH_CUSTOMER_DEALS = Constants.API_URL + '/deal/customer-deals/';
  static PATH_ADD_DEAL = Constants.API_URL + '/deal/add/';
  static PATH_EDIT_DEAL = Constants.API_URL + '/deal/edit/';
  static PATH_DELETE_DEAL = Constants.API_URL + '/deal/delete/';

  static PATH_ACCEPT_DEAL = Constants.API_URL + '/deal/accept/';
  static PATH_CANCEL_DEAL = Constants.API_URL + '/deal/cancel/';
  static PATH_COMPLETE_DEAL = Constants.API_URL + '/deal/complete/';
  static PATH_REWARD_DEAL = Constants.API_URL + '/deal/reward/';
  static PATH_REFUSE_DEAL = Constants.API_URL + '/deal/refuse/';
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
  static SUCCESS_EDIT = 'Success edit, navigated to profile';
  // map
  static BeastMap = new Map<number, string>([
    [BeastsEnum.ALP, 'Альп'], [BeastsEnum.BASILISK, 'Василиск'], [BeastsEnum.BRUXA, 'Брукса'], [BeastsEnum.DRACONID, 'Драконид'],
    [BeastsEnum.DROWNER, 'Утопец'], [BeastsEnum.GHOUL, 'Гуль'], [BeastsEnum.GOLEM, 'Голем'], [BeastsEnum.KIKIMORE, 'Кикимора'],
    [BeastsEnum.NIGHTWRAITH, 'Ночница'], [BeastsEnum.STRIGA, 'Стрыга'], [BeastsEnum.WYVERN, 'Виверна'], [BeastsEnum.OTHER, 'Другое']
  ]);
  static RegionMap = new Map<number, string>([
    [RegionsEnum.AEDIRN, 'Аэдирн'], [RegionsEnum.BROKILON, 'Брокилон'], [RegionsEnum.CIDARIS, 'Цидарис'], [RegionsEnum.CINTRA, 'Цинтра'],
    [RegionsEnum.HENGFORS, 'Хенгфорс'], [RegionsEnum.KAEDWEN, 'Каэдвен'], [RegionsEnum.KOVIR, 'Ковир'], [RegionsEnum.LYRIA, 'Лирия'],
    [RegionsEnum.REDANIA, 'Редания'], [RegionsEnum.SKELLIGE, 'Скеллиге'], [RegionsEnum.TEMERIA, 'Темерия'], [RegionsEnum.VERDEN, 'Верден']
  ]);
  static CurrencyMap = new Map<number, string>([
    [CurrencyEnum.CROWN, 'Крона'], [CurrencyEnum.OREN, 'Орен'], [CurrencyEnum.DUCAT, 'Дукат']
  ]);
  static RoleMap = new Map<number, string>([
    [RolesEnum.KING, 'Король'], [RolesEnum.USER, 'Пользователь'], [RolesEnum.WITCHER, 'Ведьмак'], [RolesEnum.VENDOR, 'Торговец'],
    [RolesEnum.BLACKSMITH, 'Ремесленник']
  ]);
}
