import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import MainPropositionTitle from '../MainPropositionTitle'
import MainPropositionPanels from '../MainPropositionPanels'
import MainPropositionContent from '../MainPropositionContent'
import { SearchMode } from '../../../../types/offer.ts'
import { AttractionType } from '../../../../types/attraction.ts'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import styles from './MainProposition.module.css'

const MainProposition = () => {
    const { t } = useHooks()

    return (
        <div className={styles.proposition}>
            <MainPropositionContent
                title={<MainPropositionTitle text={t('home.proposition.stay')} />}
                panels={
                    <MainPropositionPanels
                        firstText={t('home.proposition.guesthouses')}
                        firstTouristicPlaceType={TouristicPlaceType.GUESTHOUSE}
                        secondText={t('home.proposition.apartments')}
                        secondTouristicPlaceType={TouristicPlaceType.APARTMENT}
                        thirdText={t('home.proposition.cottages')}
                        thirdTouristicPlaceType={TouristicPlaceType.COTTAGES}
                        fourthText={t('home.proposition.hotels')}
                        fourthTouristicPlaceType={TouristicPlaceType.HOTEL}
                        fifthText={t('home.proposition.b&b')}
                        fifthTouristicPlaceType={TouristicPlaceType.BB}
                        sixthText={t('home.proposition.hostels')}
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
                        firstAttractionType={AttractionType.RELAX}
                        secondText={t('home.proposition.sport')}
                        secondAttractionType={AttractionType.SPORT}
                        thirdText={t('home.proposition.recreation')}
                        thirdAttractionType={AttractionType.RECREATION}
                        fourthText={t('home.proposition.entertainment')}
                        fourthAttractionType={AttractionType.ENTERTAINMENT}
                        fifthText={t('home.proposition.food')}
                        fifthAttractionType={AttractionType.FOOD}
                        sixthText={t('home.proposition.event')}
                        sixthAttractionType={AttractionType.EVENT}
                        mode={SearchMode.ATTRACTION}
                    />
                }
            />
        </div>
    )
}

export default MainProposition
