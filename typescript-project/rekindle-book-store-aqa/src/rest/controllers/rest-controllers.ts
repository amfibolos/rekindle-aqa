import {Bookstore, Customer, ValueObject, Product} from "@/model/domain-model";
import {AxiosResponse} from "axios";

export interface Get<T extends ValueObject, U> {
    get(param?: string): U

    getSuccessfully(param?: string): Promise<T>
}

export interface GetAll<T extends ValueObject, U> {
    getAll(param?: string): U

    getAllSuccessfully(param?: string): Promise<T[]>
}

export interface Post<T extends ValueObject, U> {
    post(param: T): U

    postSuccessfully(param: T): Promise<T>
}

export interface Put<T extends ValueObject, U> {
    put(param: T): U

    putSuccessfully(param: T): void
}

export interface Delete<T extends ValueObject, U> {
    delete(param: T): U

    deleteSuccessfully(param: T): void
}

export interface CrudController<T extends ValueObject, U> extends Get<T, U>, GetAll<T, U>, Post<T, U>, Put<T, U>, Delete<T, U> {
}

export interface BookstoreCrudController extends CrudController<Bookstore, Promise<AxiosResponse>> {
}

export interface BookstoreProductCrudController extends CrudController<Product, Promise<AxiosResponse>> {
}

export interface CustomerCrudController extends CrudController<Customer, Promise<AxiosResponse>> {
}