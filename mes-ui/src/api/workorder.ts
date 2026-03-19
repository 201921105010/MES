import http from './http'

export interface WorkOrderPayload {
  woNo?: string
  orderNo: string
  materialCode: string
  woQty: number
  priority?: number
  status?: string
}

export const createWorkOrder = async (payload: WorkOrderPayload) => {
  const { data } = await http.post('/workorders', payload)
  return data
}
