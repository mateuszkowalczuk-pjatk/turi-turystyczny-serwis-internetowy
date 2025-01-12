import { useTranslation } from 'react-i18next'
import { useAppDispatch } from './useAppDispatch.ts'
import { useNavigate } from 'react-router-dom'

export const useHooks = () => {
    const { t } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()

    return { t, dispatch, navigate }
}
