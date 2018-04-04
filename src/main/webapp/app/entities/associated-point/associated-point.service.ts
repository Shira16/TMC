import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AssociatedPoint } from './associated-point.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AssociatedPoint>;

@Injectable()
export class AssociatedPointService {

    private resourceUrl =  SERVER_API_URL + 'api/associated-points';

    constructor(private http: HttpClient) { }

    create(associatedPoint: AssociatedPoint): Observable<EntityResponseType> {
        const copy = this.convert(associatedPoint);
        return this.http.post<AssociatedPoint>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(associatedPoint: AssociatedPoint): Observable<EntityResponseType> {
        const copy = this.convert(associatedPoint);
        return this.http.put<AssociatedPoint>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AssociatedPoint>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AssociatedPoint[]>> {
        const options = createRequestOption(req);
        return this.http.get<AssociatedPoint[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AssociatedPoint[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AssociatedPoint = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AssociatedPoint[]>): HttpResponse<AssociatedPoint[]> {
        const jsonResponse: AssociatedPoint[] = res.body;
        const body: AssociatedPoint[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AssociatedPoint.
     */
    private convertItemFromServer(associatedPoint: AssociatedPoint): AssociatedPoint {
        const copy: AssociatedPoint = Object.assign({}, associatedPoint);
        return copy;
    }

    /**
     * Convert a AssociatedPoint to a JSON which can be sent to the server.
     */
    private convert(associatedPoint: AssociatedPoint): AssociatedPoint {
        const copy: AssociatedPoint = Object.assign({}, associatedPoint);
        return copy;
    }
}
