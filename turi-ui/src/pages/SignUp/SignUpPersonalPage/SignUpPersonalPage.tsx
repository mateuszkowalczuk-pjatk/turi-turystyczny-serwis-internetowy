import SignUpPersonalTitle from '../../../components/Auth/SignUpPersonal/SignUpPersonalTitle'
import SignUpPersonalDescription from '../../../components/Auth/SignUpPersonal/SignUpPersonalDescription'
import SignUpPersonalContent from '../../../components/Auth/SignUpPersonal/SignUpPersonalContent'
import styles from './SignUpPersonalPage.module.css'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

const SignUpPersonalPage = () => {
    const navigate = useNavigate()
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPersonalization = useSelector((state: RootState) => state.personal.isPersonalization)

    useEffect(() => {
        if (!isAuthenticated || !isPersonalization) {
            navigate('/login')
        }
    }, [isAuthenticated, isPersonalization, navigate])

    return (
        <div className={styles.personal}>
            <SignUpPersonalTitle />
            <SignUpPersonalDescription />
            <SignUpPersonalContent />
        </div>
    )
}

export default SignUpPersonalPage
