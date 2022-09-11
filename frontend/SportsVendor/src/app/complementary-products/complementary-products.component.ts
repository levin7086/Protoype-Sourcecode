import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {map, Observable, switchMap} from "rxjs";
import {ShoppingCart} from "../../shared/model/ShoppingCart";
import {RecommendationService} from "../../shared/services/recommendation.service";
import {Recommendation} from "../../shared/model/Recommendation";
import {currencyFormatter} from "../common_utils";
import {Product} from "../../shared/model/Product";

@Component({
  selector: 'app-complementary-products',
  templateUrl: './complementary-products.component.html',
  styleUrls: ['./complementary-products.component.css']
})
export class ComplementaryProductsComponent implements OnInit {

  @Input('shoppingCart$') shoppingCart$: Observable<ShoppingCart>;
  @Output() addToCartEvent = new EventEmitter<Product>();
  recommendations: Recommendation[]
  recommendations$: Observable<Recommendation[]>

  constructor(private recommendationService: RecommendationService) { }

  ngOnInit(): void {
    this.shoppingCart$.pipe(
      switchMap(cart => {
        let ids = cart.items.map(item => item.productID)
        return this.recommendationService.getComplementaryRecommendations('1', ids)
      })
    ).subscribe(recs => this.recommendations = recs);
  }

  formatPrice(price: number){
    return currencyFormatter(price, 'EUR ')
  }

  onClick(recommendation: Recommendation) {
    this.recommendations
      .forEach((element, index) => {
        if(element !== recommendation) return
        this.recommendations.splice(index, 1)
      })
    this.addToCartEvent.emit(recommendation.product)

  }
}
