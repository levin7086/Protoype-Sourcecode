import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Product} from "../model/Product";
import {map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CatalogService {

  constructor(private http: HttpClient) { }

  getProductsByIds(ids: String[]){
    return this.http.post<Product[]>("http://localhost:8080/product", ids).pipe(
      map(products => {
        return products.filter(value => value != null)
      })
    );
  }

  getProductById(id: string){
      return this.http.post<Product[]>("http://localhost:8080/product", [id]).pipe(map(value => value[0]));
  }
}
