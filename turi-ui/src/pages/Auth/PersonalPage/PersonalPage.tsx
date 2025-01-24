import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectSome } from '../../../hooks/shared/useRedirect.ts'
import AuthPersonal from '../../../components/Auth/AuthPersonal'

const PersonalPage = () => {
    const { isAuthenticated, isPersonal } = useStates()

    useRedirectSome([!isAuthenticated, !isPersonal], '/login')

    return <AuthPersonal />
}

export default PersonalPage
