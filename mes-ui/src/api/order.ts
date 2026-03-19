import http from './http'
import type { OrderHeader } from '../types/order'

export const listOrders = async () => {
  const { data } = await http.get<OrderHeader[]>('/orders')
  return data
}

export const createOrder = async (payload: Partial<OrderHeader>) => {
  const { data } = await http.post<OrderHeader>('/orders', payload)
  return data
}

export const updateOrder = async (id: number, payload: Partial<OrderHeader>) => {
  const { data } = await http.put<OrderHeader>(`/orders/${id}`, payload)
  return data
}

export const deleteOrder = async (id: number) => {
  const { data } = await http.delete<boolean>(`/orders/${id}`)
  return data
}
