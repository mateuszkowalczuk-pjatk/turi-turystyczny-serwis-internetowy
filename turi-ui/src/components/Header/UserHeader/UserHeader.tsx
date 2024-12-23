import { useTranslation } from 'react-i18next'
import HeaderLayout from '../HeaderLayout'
import HeaderLinks from '../HeaderLinks'
import TextRegular from '../../Controls/Text/TextRegular'
import HeaderButtons from '../HeaderButtons'
import { useNavigate } from 'react-router-dom'

const UserHeader = () => {
    const navigate = useNavigate()
    const { t } = useTranslation()

    const navigateToReservations = () => {
        console.log('Reservations')
    }

    const navigateToStays = () => {
        console.log('Stays')
    }

    const navigateToFavourites = () => {
        console.log('Favourites')
    }

    const navigateToPremium = () => {
        navigate('/premium')
    }

    return (
        <HeaderLayout
            links={
                <HeaderLinks
                    firstLink={
                        <TextRegular
                            text={t('header.reservation')}
                            onClick={navigateToReservations}
                        />
                    }
                    secondLink={
                        <TextRegular
                            text={t('header.stays')}
                            onClick={navigateToStays}
                        />
                    }
                    thirdLink={
                        <TextRegular
                            text={t('header.favourites')}
                            onClick={navigateToFavourites}
                        />
                    }
                />
            }
            buttons={
                <HeaderButtons
                    text={t('header.premium')}
                    firstOnClick={navigateToPremium}
                />
            }
        />
    )
}

export default UserHeader
