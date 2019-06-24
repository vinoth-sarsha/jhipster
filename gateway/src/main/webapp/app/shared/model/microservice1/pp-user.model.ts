import { Moment } from 'moment';

export interface IPp_user {
  id?: number;
  user_name?: string;
  dob?: Moment;
  role?: string;
  location?: string;
  about_me?: string;
}

export class Pp_user implements IPp_user {
  constructor(
    public id?: number,
    public user_name?: string,
    public dob?: Moment,
    public role?: string,
    public location?: string,
    public about_me?: string
  ) {}
}
