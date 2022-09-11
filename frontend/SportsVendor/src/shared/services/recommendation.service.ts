import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CatalogService} from "./catalog.service";
import {ShoppingCartItem} from "../model/ShoppingCartItem";
import {map, Observable, switchMap} from "rxjs";
import {ShoppingCart} from "../model/ShoppingCart";
import {CATEGORY_RECOMMENDATION, COMPLEMENTARY_RECOMMENDATION, Recommendation} from "../model/Recommendation";
import {RecommendationsDTO} from "../model/RecommendationsDTO";
import {CATEGORIES} from "../model/Category";
import {Product} from "../model/Product";

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  private categoryRecommendationURL = 'http://localhost:8083/recommendation/category/';
  private complementaryRecommendationURL = 'http://localhost:8083/recommendation/complementary/';

  constructor(private http: HttpClient, private catalogService: CatalogService) {
  }

  public getCategoryRecommendations(userId: string): Observable<Recommendation[]> {
    let url = this.categoryRecommendationURL.concat(userId);

    return this.http.get<RecommendationsDTO>(url)
      .pipe(
        map(response => {
          let recommendations: Recommendation[] = [];
          response.recommendationList.forEach(value => {
            recommendations.push({
              identifier: value,
              recommendationType: response.recommendationType
            })
            recommendations.filter(value1 => value1.recommendationType === CATEGORY_RECOMMENDATION)
              .forEach(value1 => value1.category = CATEGORIES.find(value2 => value2.name === value1.identifier));
          })
          return recommendations;
        }));
  }

  public getComplementaryRecommendations(userId: string, productIdList: string[]): Observable<Recommendation[]> {
    let url = this.complementaryRecommendationURL.concat(userId);

    return this.http.post<RecommendationsDTO>(url, productIdList)
      .pipe(
        switchMap(recommendation => {
          return this.catalogService.getProductsByIds(recommendation.recommendationList)
            .pipe(
              map(products => {
                let recommendations: Recommendation[] = [];
                recommendation.recommendationList.forEach(value => {
                  recommendations.push({
                    identifier: value,
                    recommendationType: recommendation.recommendationType
                  })
                  recommendations.filter(value1 => value1.recommendationType === COMPLEMENTARY_RECOMMENDATION)
                    .forEach(value1 => value1.product = products.find(p => p.articleNumber === value1.identifier));
                })
                return recommendations;
              })
            )
        })
      );
  }

}

// switchMap(response => {
//   return response.forEach(rec => {
//     rec.product = this.catalogService.getProductById(rec.identifier)
//     return rec;
//   })
//
//   // return this.catalogService.getProductsById(response.map(v => v)
//   //   .pipe(
//   //     map(products => {
//   //       let cart: ShoppingCart = new ShoppingCart();
//   //       cart.userId = userId;
//   //       response.forEach(i => {
//   //         i.product = products.filter(p => p.articleNumber == i.productID)[0]
//   //         cart.items.push(i);
//   //       })
//   //       return cart;
//       }));
