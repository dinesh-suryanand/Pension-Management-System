import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { CustomHttpRespone } from '../model/custom-http-response';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = environment.authenticationUrl;

  constructor(private httpClient: HttpClient) { }

  public getUsers() : Observable<User[] | HttpErrorResponse> {
    return this.httpClient.get<User[]>(`${this.host}/user/list`);
  }

  public addUser(formData: FormData) : Observable<User | HttpErrorResponse> {
    return this.httpClient.post<User>(`${this.host}/user/add`, formData);
  }

  public updateUser(formData: FormData) : Observable<User | HttpErrorResponse> {
    return this.httpClient.post<User>(`${this.host}/user/update`, formData);
  }

  public resetPassword(email: string) : Observable<CustomHttpRespone | HttpErrorResponse> {
    return this.httpClient.get<CustomHttpRespone>(`${this.host}/user/resetpassword/${email}`);
  }

  public updateProfileImage(formData: FormData) : Observable<HttpEvent<User> | HttpErrorResponse> {
    return this.httpClient.post<User>(`${this.host}/user/updateProfileImage`, formData, {reportProgress: true, observe: 'events'});
  }

  public deleteUser(userId: number): Observable<CustomHttpRespone> {
    return this.httpClient.delete<CustomHttpRespone>(`${this.host}/user/delete/${userId}`);
  }

  public addUsersToLocalCache(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  public getUsersFromLocalCache(): User[] | null {
    if (localStorage.getItem('users')) {
        return JSON.parse(JSON.stringify(localStorage.getItem('users')));
    }
    return null;
  }

  public createUserFormDate(loggedInUsername: string, user: User, profileImage: File): FormData {
    const formData = new FormData();
    formData.append('currentUsername', loggedInUsername);
    formData.append('firstName', user.firstName);
    formData.append('lastName', user.lastName);
    formData.append('username', user.username);
    formData.append('email', user.email);
    formData.append('role', user.role);
    formData.append('profileImage', profileImage);
    formData.append('isActive', JSON.stringify(user.active));
    formData.append('isNonLocked', JSON.stringify(user.notLocked));
    return formData;
  }
}
