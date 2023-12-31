export default interface IUser {
  id?: any | null,
  username: string,
  email: string,
  password: string,
  money: number,
  roles?: Array<string>
}