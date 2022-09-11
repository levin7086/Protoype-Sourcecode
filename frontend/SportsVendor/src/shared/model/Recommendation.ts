import {Product} from "./Product";
import {Category} from "./Category";

export const CATEGORY_RECOMMENDATION = 'PRODUCT_CATEGORY'
export const COMPLEMENTARY_RECOMMENDATION = 'SHOPPING_CART'

export interface Recommendation{
  identifier: string
  recommendationType: string
  product?: Product
  category?: Category
}
