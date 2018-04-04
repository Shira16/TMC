import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ControlPoint } from './control-point.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ControlPoint>;

@Injectable()
export class ControlPointService {

    private resourceUrl =  SERVER_API_URL + 'api/control-points';

    constructor(private http: HttpClient) { }

    create(controlPoint: ControlPoint): Observable<EntityResponseType> {
        const copy = this.convert(controlPoint);
        return this.http.post<ControlPoint>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(controlPoint: ControlPoint): Observable<EntityResponseType> {
        const copy = this.convert(controlPoint);
        return this.http.put<ControlPoint>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ControlPoint>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ControlPoint[]>> {
        const options = createRequestOption(req);
        return this.http.get<ControlPoint[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ControlPoint[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ControlPoint = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ControlPoint[]>): HttpResponse<ControlPoint[]> {
        const jsonResponse: ControlPoint[] = res.body;
        const body: ControlPoint[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ControlPoint.
     */
    private convertItemFromServer(controlPoint: ControlPoint): ControlPoint {
        const copy: ControlPoint = Object.assign({}, controlPoint);
        return copy;
    }

    /**
     * Convert a ControlPoint to a JSON which can be sent to the server.
     */
    private convert(controlPoint: ControlPoint): ControlPoint {
        const copy: ControlPoint = Object.assign({}, controlPoint);
        return copy;
    }
}
