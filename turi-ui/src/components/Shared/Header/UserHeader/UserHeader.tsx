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
                            text={t('header.reservation')}
                            onClick={() => console.log('Reservations')}
                        />
                    }
                    secondLink={
                        <TextRegular
                            text={t('header.stays')}
                            onClick={() => console.log('Stays')}
                        />
                    }
                    thirdLink={
                        <TextRegular
                            text={t('header.favourites')}
                            onClick={() => console.log('Favourites')}
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
