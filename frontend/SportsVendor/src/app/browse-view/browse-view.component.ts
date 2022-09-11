import {Component, OnInit} from '@angular/core';
import {ColDef} from "ag-grid-community";
import {currencyFormatter} from "../common_utils";
import {Observable, take} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {
  AddToCartCellRendererComponent
} from "../../shared/cell-renderer/add-to-cart-cell-renderer.component";
import {BEACH_VOLLEYBALL, Category, FOOTBALL, INDOOR_VOLLEYBALL, TENNIS} from "../../shared/model/Category";
import {RecommendationService} from "../../shared/services/recommendation.service";

@Component({
  selector: 'app-browse-view',
  templateUrl: './browse-view.component.html',
  styleUrls: ['./browse-view.component.css']
})
export class BrowseViewComponent implements OnInit {

  displayedCategories: Category[]
  recommendedCategories: Category[] = []

  constructor(private recommendationService: RecommendationService) {
  }

  ngOnInit(): void {
    this.displayedCategories = [BEACH_VOLLEYBALL, INDOOR_VOLLEYBALL, TENNIS, FOOTBALL]
    this.recommendationService.getCategoryRecommendations('1')
      .pipe(take(1))
      .subscribe(rec => {
        this.recommendedCategories =
          rec
            .filter(value => value.category !== undefined)
            .map(value => value.category!)
            .slice(0,2)
        this.displayedCategories = [...new Set([...this.recommendedCategories, ...this.displayedCategories])]
      })
  }
}
