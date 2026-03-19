import http from './http'

export interface BomExplodeItem {
  materialCode: string
  requiredQty: number
  level: number
}

export const explodeBom = async (productCode: string, qty: number) => {
  const { data } = await http.post<BomExplodeItem[]>('/bom/explode', { productCode, qty })
  return data
}
