import { useTranslation } from 'react-i18next'
import MainPropositionContent from '../MainPropositionContent'
import MainPropositionTitle from '../MainPropositionTitle'
import MainPropositionPanels from '../MainPropositionPanels'
import { SearchMode } from '../../../../types/search.ts'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import { AttractionType } from '../../../../types/attraction.ts'
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
                        firstTouristicPlaceType={TouristicPlaceType.GUESTHOUSE}
                        secondText={t('home.proposition.apartments')}
                        secondImage={'src/assets/images/proposition/stay/apartment.jpeg'}
                        secondTouristicPlaceType={TouristicPlaceType.APARTMENT}
                        thirdText={t('home.proposition.cottages')}
                        thirdImage={'src/assets/images/proposition/stay/cottages.jpeg'}
                        thirdTouristicPlaceType={TouristicPlaceType.COTTAGES}
                        fourthText={t('home.proposition.hotels')}
                        fourthImage={'src/assets/images/proposition/stay/hotel.jpeg'}
                        fourthTouristicPlaceType={TouristicPlaceType.HOTEL}
                        fifthText={t('home.proposition.b&b')}
                        fifthImage={'src/assets/images/proposition/stay/b&b.jpeg'}
                        fifthTouristicPlaceType={TouristicPlaceType.BB}
                        sixthText={t('home.proposition.hostels')}
                        sixthImage={'src/assets/images/proposition/stay/hostel.jpeg'}
                        sixthTouristicPlaceType={TouristicPlaceType.HOSTEL}
                        mode={SearchMode.STAY}
                    />
                }
            />
            <MainPropositionContent
                title={<MainPropositionTitle text={t('home.proposition.attraction')} />}
                panels={
                    <MainPropositionPanels
                        firstText={t('home.proposition.relax')}
                        firstImage={'src/assets/images/proposition/attraction/relax.jpeg'}
                        firstAttractionType={AttractionType.RELAX}
                        secondText={t('home.proposition.sport')}
                        secondImage={'src/assets/images/proposition/attraction/sport.jpeg'}
                        secondAttractionType={AttractionType.SPORT}
                        thirdText={t('home.proposition.recreation')}
                        thirdImage={'src/assets/images/proposition/attraction/recreation.jpeg'}
                        thirdAttractionType={AttractionType.RECREATION}
                        fourthText={t('home.proposition.entertainment')}
                        fourthImage={'src/assets/images/proposition/attraction/entertainment.jpeg'}
                        fourthAttractionType={AttractionType.ENTERTAINMENT}
                        fifthText={t('home.proposition.food')}
                        fifthImage={'src/assets/images/proposition/attraction/food.jpeg'}
                        fifthAttractionType={AttractionType.FOOD}
                        sixthText={t('home.proposition.event')}
                        sixthImage={'src/assets/images/proposition/attraction/event.jpeg'}
                        sixthAttractionType={AttractionType.EVENT}
                        mode={SearchMode.ATTRACTION}
                    />
                }
            />
        </div>
    )
}

export default MainProposition
