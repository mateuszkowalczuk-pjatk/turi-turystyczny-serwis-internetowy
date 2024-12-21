import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import HeaderLayout from '../HeaderLayout'
import HeaderLinks from '../HeaderLinks'
import TextRegular from '../../Controls/Text/TextRegular'

const PremiumHeader = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    return (
        <HeaderLayout
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
                    fourthLink={
                        <TextRegular
                            text={t('header.tourism')}
                            onClick={() => navigate('/tourism')}
                        />
                    }
                    isPremium
                />
            }
        />
    )
}

export default PremiumHeader
