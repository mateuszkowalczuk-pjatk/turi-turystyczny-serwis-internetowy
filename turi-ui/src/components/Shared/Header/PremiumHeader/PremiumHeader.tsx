import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import HeaderLinks from '../HeaderLinks'
import TextRegular from '../../Controls/Text/TextRegular'
import HeaderContent from '../HeaderContent'
import HeaderButtons from '../HeaderButtons'

const PremiumHeader = () => {
    const { t, navigate } = useHooks()

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
                    fourthLink={
                        <TextRegular
                            text={t('header.tourism')}
                            onClick={() => navigate('/tourism')}
                        />
                    }
                    isPremium
                />
            }
            buttons={
                <HeaderButtons
                    text={t('header.premium')}
                    firstOnClick={() => navigate('/offers')}
                />
            }
        />
    )
}

export default PremiumHeader
