import http from './http'
import type { ScheduleTask } from '../types/schedule'

export const listScheduleTasks = async () => {
  const { data } = await http.get<ScheduleTask[]>('/schedules/tasks')
  return data
}

export const createScheduleTask = async (payload: Partial<ScheduleTask>) => {
  const { data } = await http.post<ScheduleTask>('/schedules/tasks', payload)
  return data
}
