import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { RouteOne } from './route-one.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RouteOne>;

@Injectable()
export class RouteOneService {

    private resourceUrl =  SERVER_API_URL + 'api/route-ones';

    constructor(private http: HttpClient) { }

    create(routeOne: RouteOne): Observable<EntityResponseType> {
        const copy = this.convert(routeOne);
        return this.http.post<RouteOne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(routeOne: RouteOne): Observable<EntityResponseType> {
        const copy = this.convert(routeOne);
        return this.http.put<RouteOne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RouteOne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RouteOne[]>> {
        const options = createRequestOption(req);
        return this.http.get<RouteOne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RouteOne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RouteOne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RouteOne[]>): HttpResponse<RouteOne[]> {
        const jsonResponse: RouteOne[] = res.body;
        const body: RouteOne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RouteOne.
     */
    private convertItemFromServer(routeOne: RouteOne): RouteOne {
        const copy: RouteOne = Object.assign({}, routeOne);
        return copy;
    }

    /**
     * Convert a RouteOne to a JSON which can be sent to the server.
     */
    private convert(routeOne: RouteOne): RouteOne {
        const copy: RouteOne = Object.assign({}, routeOne);
        return copy;
    }
}
