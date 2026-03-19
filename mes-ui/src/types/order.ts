export interface OrderHeader {
  id?: number
  orderNo: string
  productCode: string
  orderQty: number
  dueTime: string
  priority?: number
  isUrgent?: boolean
  status: string
  sourceType?: string
}
