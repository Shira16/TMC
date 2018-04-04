import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Marker } from './marker.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Marker>;

@Injectable()
export class MarkerService {

    private resourceUrl =  SERVER_API_URL + 'api/markers';

    constructor(private http: HttpClient) { }

    create(marker: Marker): Observable<EntityResponseType> {
        const copy = this.convert(marker);
        return this.http.post<Marker>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(marker: Marker): Observable<EntityResponseType> {
        const copy = this.convert(marker);
        return this.http.put<Marker>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Marker>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Marker[]>> {
        const options = createRequestOption(req);
        return this.http.get<Marker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Marker[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Marker = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Marker[]>): HttpResponse<Marker[]> {
        const jsonResponse: Marker[] = res.body;
        const body: Marker[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Marker.
     */
    private convertItemFromServer(marker: Marker): Marker {
        const copy: Marker = Object.assign({}, marker);
        return copy;
    }

    /**
     * Convert a Marker to a JSON which can be sent to the server.
     */
    private convert(marker: Marker): Marker {
        const copy: Marker = Object.assign({}, marker);
        return copy;
    }
}
