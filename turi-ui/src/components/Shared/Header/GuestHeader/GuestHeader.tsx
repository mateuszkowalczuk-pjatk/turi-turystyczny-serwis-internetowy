import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import HeaderContent from '../HeaderContent'
import HeaderButtons from '../HeaderButtons'

const GuestHeader = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    return (
        <HeaderContent
            buttons={
                <HeaderButtons
                    text={t('header.sign-in-button')}
                    firstOnClick={() => navigate('/login')}
                    secondOnClick={() => navigate('/register')}
                />
            }
        />
    )
}

export default GuestHeader
