import { useTranslation } from 'react-i18next'
import { useAppDispatch } from '../app/useAppDispatch.ts'
import { useNavigate, useLocation } from 'react-router-dom'

export const useHooks = () => {
    const { t, i18n } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const location = useLocation()

    return { t, dispatch, navigate, location, i18n }
}
