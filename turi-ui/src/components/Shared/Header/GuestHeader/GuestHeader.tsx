import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import HeaderContent from '../HeaderContent'
import HeaderButtons from '../HeaderButtons'

const GuestHeader = () => {
    const { t, navigate } = useHooks()

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
