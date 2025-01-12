import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import HeaderContent from '../HeaderContent'
import HeaderLinks from '../HeaderLinks'
import TextRegular from '../../Controls/Text/TextRegular'
import HeaderButtons from '../HeaderButtons'

const UserHeader = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    return (
        <HeaderContent
            links={
                <HeaderLinks
                    firstLink={
                        <TextRegular
                            text={t('header.reservations')}
                            onClick={() => navigate('reservations')}
                        />
                    }
                    secondLink={
                        <TextRegular
                            text={t('header.realized')}
                            onClick={() => navigate('/realized')}
                        />
                    }
                    thirdLink={
                        <TextRegular
                            text={t('header.favourites')}
                            onClick={() => navigate('/favourites')}
                        />
                    }
                />
            }
            buttons={
                <HeaderButtons
                    text={t('header.premium')}
                    firstOnClick={() => navigate('/premium')}
                />
            }
        />
    )
}

export default UserHeader
