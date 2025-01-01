import { useTranslation } from 'react-i18next'
import MainPropositionContent from '../MainPropositionContent'
import MainPropositionTitle from '../MainPropositionTitle'
import MainPropositionPanels from '../MainPropositionPanels'
import styles from './MainProposition.module.css'

const MainProposition = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.proposition}>
            <MainPropositionContent
                title={<MainPropositionTitle text={t('home.proposition.stay')} />}
                panels={
                    <MainPropositionPanels
                        firstText={t('home.proposition.guesthouses')}
                        firstImage={'src/assets/images/proposition/stay/guesthouse.jpeg'}
                        secondText={t('home.proposition.apartments')}
                        secondImage={'src/assets/images/proposition/stay/apartment.jpeg'}
                        thirdText={t('home.proposition.cottages')}
                        thirdImage={'src/assets/images/proposition/stay/cottages.jpeg'}
                        fourthText={t('home.proposition.hotels')}
                        fourthImage={'src/assets/images/proposition/stay/hotel.jpeg'}
                        fifthText={t('home.proposition.b&b')}
                        fifthImage={'src/assets/images/proposition/stay/b&b.jpeg'}
                        sixthText={t('home.proposition.hostels')}
                        sixthImage={'src/assets/images/proposition/stay/hostel.jpeg'}
                    />
                }
            />
            <MainPropositionContent
                title={<MainPropositionTitle text={t('home.proposition.attraction')} />}
                panels={
                    <MainPropositionPanels
                        firstText={t('home.proposition.relax')}
                        firstImage={'src/assets/images/proposition/attraction/relax.jpeg'}
                        secondText={t('home.proposition.sport')}
                        secondImage={'src/assets/images/proposition/attraction/sport.jpeg'}
                        thirdText={t('home.proposition.recreation')}
                        thirdImage={'src/assets/images/proposition/attraction/recreation.jpeg'}
                        fourthText={t('home.proposition.entertainment')}
                        fourthImage={'src/assets/images/proposition/attraction/entertainment.jpeg'}
                        fifthText={t('home.proposition.food')}
                        fifthImage={'src/assets/images/proposition/attraction/food.jpeg'}
                        sixthText={t('home.proposition.event')}
                        sixthImage={'src/assets/images/proposition/attraction/event.jpeg'}
                    />
                }
            />
        </div>
    )
}

export default MainProposition
