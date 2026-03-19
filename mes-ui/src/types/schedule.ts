export interface ScheduleTask {
  id?: number
  scheduleNo: string
  woNo: string
  opNo: number
  workCenterCode: string
  equipmentCode?: string
  shiftCode?: string
  planStartTime: string
  planEndTime: string
  priorityScore?: number
  scheduleStatus?: string
}
