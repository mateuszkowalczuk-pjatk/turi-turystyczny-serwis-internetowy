import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import HeaderLayout from '../HeaderLayout'
import HeaderButtons from '../HeaderButtons'

const GuestHeader = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    return (
        <HeaderLayout
            buttons={
                <HeaderButtons
                    text={t('header.sign-in-button')}
                    firstOnClick={() => navigate('/login')}
                    secondOnClick={() => navigate('/signup')}
                />
            }
        />
    )
}

export default GuestHeader
